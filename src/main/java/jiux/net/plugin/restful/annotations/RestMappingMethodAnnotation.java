package jiux.net.plugin.restful.annotations;

public enum RestMappingMethodAnnotation {
    REST_MAPPING("com.timevale.mandarin.common.annotation.RestMapping", null);

    private final String methodName;

    private final String qualifiedName;

    RestMappingMethodAnnotation(String qualifiedName, String methodName) {
        this.qualifiedName = qualifiedName;
        this.methodName = methodName;
    }

    public String methodName() {
        return this.methodName;
    }

    public String getQualifiedName() {
        return this.qualifiedName;
    }

    public String getShortName() {
        return this.qualifiedName.substring(this.qualifiedName.lastIndexOf(".") - 1);
    }

    public static RestMappingMethodAnnotation getByQualifiedName(String qualifiedName) {
        for (RestMappingMethodAnnotation springRequestAnnotation : values()) {
            if (springRequestAnnotation.getQualifiedName().equals(qualifiedName))
                return springRequestAnnotation;
        }
        return null;
    }

    public static RestMappingMethodAnnotation getByShortName(String requestMapping) {
        for (RestMappingMethodAnnotation springRequestAnnotation : values()) {
            if (springRequestAnnotation.getQualifiedName().endsWith(requestMapping))
                return springRequestAnnotation;
        }
        return null;
    }
}
