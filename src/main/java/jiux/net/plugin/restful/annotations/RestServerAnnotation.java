package jiux.net.plugin.restful.annotations;

public enum RestServerAnnotation implements PathMappingAnnotation {
    REST_SERVICE("RestService", "com.timevale.mandarin.common.annotation.RestService"),
    EXTERNAL_SERVICE("ExternalService", "com.timevale.mandarin.common.annotation.ExternalService");

    private final String shortName;

    private final String qualifiedName;

    RestServerAnnotation(String shortName, String qualifiedName) {
        this.shortName = shortName;
        this.qualifiedName = qualifiedName;
    }

    public String getQualifiedName() {
        return this.qualifiedName;
    }

    public String getShortName() {
        return this.shortName;
    }
}
