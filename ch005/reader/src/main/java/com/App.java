package com;

import java.util.Arrays;

/**
 * @author jayying
 * @date 2021/1/7
 */
public class App {
    public static void main(String[] args) throws Exception {
        String encodingName = args[0];
        String input = args[1];

        Decoder decoder = DecoderFactory.getDecoder(encodingName);
        String[] result = decoder.getContent(input);
        System.out.println("converted result="+ Arrays.asList(result));
    }
}
