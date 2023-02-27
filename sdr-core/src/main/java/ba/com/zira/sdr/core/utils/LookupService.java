package ba.com.zira.sdr.core.utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import ba.com.zira.sdr.api.enums.ObjectType;
import ba.com.zira.sdr.api.model.media.CoverImageHelper;
import ba.com.zira.sdr.dao.CountryDAO;
import ba.com.zira.sdr.dao.LabelDAO;
import ba.com.zira.sdr.dao.MediaStoreDAO;
import ba.com.zira.sdr.dao.PersonDAO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LookupService {

    @Value("${image.default.url:http://172.20.20.45:82//vigor//img/mario.jpg}")
    String defaultImageUrl;

    @NonNull
    MediaStoreDAO mediaStoreDAO;

    @NonNull
    PersonDAO personDAO;

    @NonNull
    LabelDAO labelDAO;

    @NonNull
    CountryDAO countryDAO;

    private static SecureRandom random = new SecureRandom();

    public static String get(final Long key, final Map<Long, String> lookup) {
        if (key != null) {
            return lookup.get(key) == null ? null : lookup.get(key);
        }
        return null;
    }

    public static Long gets(final Long key, final Map<Long, Long> lookup) {
        if (key != null) {
            return lookup.get(key) == null ? null : lookup.get(key);
        }
        return null;
    }

    public static String getString(final String key, final Map<String, String> lookup) {
        if (key != null) {
            return lookup.get(key) == null ? null : lookup.get(key);
        }
        return null;
    }

    public static Double getDouble(final Long key, final Map<Long, Double> lookup) {
        if (key != null) {
            return lookup.get(key) == null ? null : lookup.get(key);
        }
        return null;
    }

    public static LocalDateTime getDate(final Long key, final Map<Long, LocalDateTime> lookup) {
        if (key != null) {
            return lookup.get(key) == null ? null : lookup.get(key);
        }
        return null;
    }

    public static String getUrl(final Long key, final Map<Long, List<CoverImageHelper>> lookup, final Random rand) {
        if (key != null) {
            if (rand != null) {
                return lookup.get(key) == null ? null : lookup.get(key).get(rand.nextInt(lookup.get(key).size())).getUrl();
            } else {
                return lookup.get(key) == null ? null : lookup.get(key).get(0).getUrl();
            }
        }
        return null;
    }

    public <E> void lookupCoverImage(final List<E> values, final Function<E, Long> getter, final String objectType,
            final BiConsumer<E, String> setter, final Function<E, String> getterForImage) {

        List<Long> ids = values.parallelStream().map(getter).distinct().collect(Collectors.toList());

        if (!(ids == null || ids.isEmpty())) {
            Map<Long, List<CoverImageHelper>> lookup = mediaStoreDAO.getUrlsForList(ids, objectType, "COVER_IMAGE");
            values.parallelStream().forEach(r -> setter.accept(r, getUrl(getter.apply(r), lookup, random)));
            values.parallelStream().forEach(r -> handleDefaultImage(r, setter, getterForImage));
        }
    }

    public <E> void lookupMultiSearchCoverImage(final List<E> values, final Function<E, Long> getterForId,
            final Function<E, String> getterForType, final BiConsumer<E, String> setter, final Function<E, String> getterForImage) {

        Map<String, List<E>> mapByType = values.parallelStream().collect(Collectors.groupingBy(getterForType));

        for (Map.Entry<String, List<E>> entry : mapByType.entrySet()) {
            lookupCoverImage(entry.getValue(), getterForId, entry.getKey(), setter, getterForImage);
        }
    }

    public <E> void handleDefaultImage(final E r, final BiConsumer<E, String> setter, final Function<E, String> getterForImage) {
        if (getterForImage.apply(r) == null) {
            setter.accept(r, defaultImageUrl);
        }

    }

    public <E> void lookupObjectNamesByIdAndType(final String objectType, final List<E> values, final Function<E, Long> getter,
            final BiConsumer<E, String> setter) {

        if (ObjectType.PERSON.getValue().equalsIgnoreCase(objectType)) {
            lookupPersonNames(values, getter, setter);
        }

        if (ObjectType.LABEL.getValue().equalsIgnoreCase(objectType)) {
            lookupLabelNames(values, getter, setter);
        }

    }

    public <E> void lookupPersonNames(List<E> values, Function<E, Long> getter, BiConsumer<E, String> setter) {
        List<Long> ids = values.parallelStream().map(getter).distinct().collect(Collectors.toList());

        if (!(ids == null || ids.isEmpty())) {
            Map<Long, String> lookup = new ConcurrentHashMap<>(personDAO.getPersonNames(ids));
            values.parallelStream().forEach(r -> setter.accept(r, get(getter.apply(r), lookup)));
        }

    }

    public <E> void lookupCountryAbbriviation(List<E> values, Function<E, Long> getter, BiConsumer<E, String> setter) {
        List<Long> ids = values.parallelStream().map(getter).distinct().collect(Collectors.toList());

        if (!(ids == null || ids.isEmpty())) {
            Map<Long, String> lookup = new ConcurrentHashMap<>(countryDAO.getFlagAbbs(ids));
            values.parallelStream().forEach(r -> setter.accept(r, get(getter.apply(r), lookup)));
        }

    }

    public <E> void lookupAudio(List<E> values, Function<E, Long> getter, BiConsumer<E, String> setter) {
        List<Long> ids = values.parallelStream().map(getter).distinct().collect(Collectors.toList());

        if (!(ids == null || ids.isEmpty())) {
            Map<Long, List<CoverImageHelper>> lookup = mediaStoreDAO.getUrlsForList(ids, ObjectType.SONG.getValue(), "AUDIO");
            values.parallelStream().forEach(r -> setter.accept(r, getUrl(getter.apply(r), lookup, null)));
        }
        
    public <E> void lookupLabelNames(List<E> values, Function<E, Long> getter, BiConsumer<E, String> setter) {
        List<Long> ids = values.parallelStream().map(getter).distinct().collect(Collectors.toList());

        if (!(ids == null || ids.isEmpty())) {
            Map<Long, String> lookup = new ConcurrentHashMap<>(labelDAO.getLabelNames(ids));
            values.parallelStream().forEach(r -> setter.accept(r, get(getter.apply(r), lookup)));
        }

    }

}
