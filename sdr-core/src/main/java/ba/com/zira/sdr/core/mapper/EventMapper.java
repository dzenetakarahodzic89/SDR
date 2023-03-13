package ba.com.zira.sdr.core.mapper;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import ba.com.zira.sdr.api.model.event.EventResponse;
import ba.com.zira.sdr.dao.model.EventEntity;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "startDate", source = "eventEntity", qualifiedByName = "startExtractor")
    @Mapping(target = "endDate", source = "eventEntity", qualifiedByName = "endExtractor")
    @Mapping(target = "lastUpdated", source = "eventEntity", qualifiedByName = "lastUpdateExtractor")
    @Mapping(target = "ended", source = "ended", qualifiedByName = "booleanExtractor")
    @Mapping(target = "cancelled", source = "cancelled", qualifiedByName = "booleanExtractor")
    @Mapping(target = "time", source = "time", qualifiedByName = "timeExtractor")
    EventResponse entityToDto(EventEntity eventEntity);

    @Named("timeExtractor")
    default LocalTime timeToLocalTime(Time time) {
        if (time == null) {
            return null;
        }
        return time.toLocalTime();
    }

    @Named("booleanExtractor")
    default Boolean stringToBoolean(String value) {
        return value.equalsIgnoreCase("t");
    }

    @Named("lastUpdateExtractor")
    default LocalDateTime timestampToLastUpdated(EventEntity eventEntity) {
        return LocalDateTime.of(eventEntity.getLastUpdated().toLocalDate(), eventEntity.getLastUpdated().toLocalTime());
    }

    @Named("startExtractor")
    default LocalDate longsToBeginDate(EventEntity eventEntity) {
        if (eventEntity.getBeginDateDay() == null || eventEntity.getBeginDateMonth() == null || eventEntity.getBeginDateYear() == null) {
            return null;
        }
        return LocalDate.of(eventEntity.getBeginDateYear().intValue(), eventEntity.getBeginDateMonth().intValue(),
                eventEntity.getBeginDateDay().intValue());
    }

    @Named("endExtractor")
    default LocalDate longsToEndDate(EventEntity eventEntity) {
        if (eventEntity.getEndDateDay() == null || eventEntity.getEndDateMonth() == null || eventEntity.getEndDateYear() == null) {
            return null;
        }
        return LocalDate.of(eventEntity.getEndDateYear().intValue(), eventEntity.getEndDateMonth().intValue(),
                eventEntity.getEndDateDay().intValue());
    }

    List<EventResponse> entitiesToDtos(List<EventEntity> eventEntities);
}
