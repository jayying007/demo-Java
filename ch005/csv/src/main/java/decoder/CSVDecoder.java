package decoder;

import com.Decoder;

/**
 * @author jayying
 * @date 2021/1/7
 */
public class CSVDecoder implements Decoder {
    @Override
    public boolean isEncodingSupported(String encodingName) {
        return "text/csv".equalsIgnoreCase(encodingName);
    }

    @Override
    public String[] getContent(String input) {
        return input.split(",");
    }
}
