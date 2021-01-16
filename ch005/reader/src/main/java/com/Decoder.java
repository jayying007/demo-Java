package com;

/**
 * @author jayying
 * @date 2021/1/7
 */
public interface Decoder {
    boolean isEncodingSupported(String encodingName);
    String[] getContent(String input);
}