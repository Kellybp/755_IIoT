public class DataProcessor {
    public String processData(String data) {
        String processedData = null;
        if (null != data && !data.isEmpty()) {
            int count = Integer.parseInt(data);
            processedData = count + " ; " + count * count + " ; " + count * count * count;
        }
        System.out.println(processedData);
        return processedData;
    }
}
