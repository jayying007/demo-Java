package com;

import com.Decoder;

import java.util.ServiceLoader;

/**
 * @author jayying
 * @date 2021/1/7
 */
public class DecoderFactory {
    public static Decoder getDecoder(String encodingName) throws Exception{
        ServiceLoader<Decoder> sl = ServiceLoader.load(Decoder.class);
        for(Decoder decode : sl) {
            if(decode.isEncodingSupported(encodingName)) {
                return decode;
            }
        }
        throw new Exception("Not supported encoding:" + encodingName);
    }
}