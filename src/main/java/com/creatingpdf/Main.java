package com.creatingpdf;

import org.apache.commons.lang3.CharSetUtils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException, CharacterCodingException {
        Service service = new Service();
        String fileStr = service.readPdf();
//        String asd = "ïîñòóïèòü";
//        String asd1 = Arrays.toString(asd.getBytes(StandardCharsets.UTF_8));
//        System.out.println(asd1);
//        System.out.println(Arrays.toString(asd.getBytes()));
         String asd = new String("î".getBytes(), Charset.forName("UTF-8"));
//        System.out.println(asd);
//        System.out.println(Charset.forName("KOI8-R"));
        byte[] encoded_input = Charset.forName("windows-1251")
                .encode(fileStr)
                .array();
        String collect = IntStream.range(0, encoded_input.length)
                .map(i -> encoded_input[i])
                .mapToObj(e -> Integer.toBinaryString(e ^ 255))
                .map(e -> String.format("%1$" + Byte.SIZE + "s", e).replace(" ", "0"))
                .collect(Collectors.joining(" "));
//        System.out.println(collect);
//        System.out.println(Arrays.toString(collect.getBytes(StandardCharsets.UTF_8)));


        ByteBuffer buffer = StandardCharsets.UTF_8.encode("ïîñòóïèòü");
        String utf8EncodedString = StandardCharsets.UTF_8.decode(buffer).toString();
//        System.out.println(new String(buffer.array()));
//        System.out.println("ïîñòóïèòü".getBytes(Charset.forName("windows-1251")));


//        byte[] bytearray = "ïîñòóïèòü".getBytes();
//        CharsetDecoder d = Charset.forName( "" ).newDecoder();
//        CharBuffer r = d.decode( ByteBuffer.wrap( bytearray ) );
//        System.out.println(r);

        String str = new String(". Ïðåäëîæèòü åìó îáðàòèòüñÿ â áëèæàéøóþ áîëüíèöó".getBytes("Cp1252"), "Cp1251");
        System.out.println(str);
    }
}
