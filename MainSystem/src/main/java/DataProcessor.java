public class DataProcessor {
    public String processData(String data) {
        String processedData = null;
        if (null != data && !data.isEmpty()) {
            int waterPressure = Integer.parseInt(data);
            if (waterPressure >= 14 && waterPressure <= 17) {
                processedData = "Pressure within the permissible range: " + waterPressure;
            } else if (waterPressure < 14) {
                processedData = "Pressure out of the permissible range:" + waterPressure + ". Adding : " + (14 - waterPressure);
            } else {
                processedData = "Pressure out of the permissible range:" + waterPressure + " Reducing : " + (waterPressure - 17);
            }
        }
        return processedData;
    }
}
