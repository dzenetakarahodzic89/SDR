package ba.com.zira.sdr.rssfeed;

public class RssAdapterFactory {
    private RssAdapterFactory() {
    }

    public static RssReader build(String adapter) {
        if (RssAdapterType.DX.getValue().equalsIgnoreCase(adapter)) {
            return new DXAdapter();
        } else if (RssAdapterType.BILLBOARD.getValue().equalsIgnoreCase(adapter)) {
            return new BillboardAdapter();
        } else if (RssAdapterType.LOUDWIRE.getValue().equalsIgnoreCase(adapter)) {
            return new LoudwireAdapter();
        } else if (RssAdapterType.EDM.getValue().equalsIgnoreCase(adapter)) {
            return new EdmAdapter();
        } else if (RssAdapterType.UCR.getValue().equalsIgnoreCase(adapter)) {
            return new UcrAdapter();
        }
        return null;
    }
}
