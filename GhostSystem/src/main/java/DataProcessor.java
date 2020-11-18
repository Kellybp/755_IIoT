public class DataProcessor {
    public String processData(String data) {
        String processedData = null;
        if (null != data && !data.isEmpty()) {
            int waterPressure = Integer.parseInt(data);
            if (waterPressure >= 11 && waterPressure <= 19) {
                processedData = "Pressure within the permissible range: " + waterPressure;
            } else if (waterPressure < 11) {
                processedData = "Pressure out of the permissible range:" + waterPressure + ". Adding : " + (11 - waterPressure);
            } else {
                processedData = "Pressure out of the permissible range:" + waterPressure + " Reducing : " + (waterPressure - 19);
            }
        }
        return processedData;
    }
}
