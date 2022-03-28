package com.policequestions;

import com.creatingpdf.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.stream.Collectors;

public class PdfToJSONService {
    public static String pdfsToJSON(List<String> filesName) {
        Root root = new Root();
        List<Group> groups = new ArrayList<>();
        filesName.forEach(each -> {
            Group group = new Group(filesName.indexOf(each) + 1, "Խումբ " + filesName.indexOf(each) + 1, pdfToJSON(each));
            groups.add(group);
        });
        root.setGroups(groups);
        ObjectMapper objectMapper = new ObjectMapper();
        String result = "";
        try {
            result = objectMapper.writeValueAsString(root);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Question> pdfToJSON(String fileName) {
        List<String> pdf = makeNormalPdf(fileName);
        List<Question> questions = new ArrayList<>();
        pdf.forEach(each -> {
            System.out.println(each);
            Question question = toClass(each, pdf.indexOf(each));
            questions.add(question);
        });
        return questions;
    }

    private static Question toClass(String questionAsString, int index) {
        questionAsString = questionAsString.trim();
        if(Character.isDigit(questionAsString.charAt(0))){
            questionAsString = questionAsString.replace(questionAsString.charAt(0) + "", "");
        }
        if(Character.isDigit(questionAsString.charAt(1))){
            questionAsString = questionAsString.replace(questionAsString.charAt(1) + "", "");
        }

        Question questionObj = null;
        String[] split = questionAsString.split("\n");
        String correctAnswerAsString = split[split.length - 1].replace("\r", "").trim();
        int correctAnswer = Integer.parseInt(correctAnswerAsString);
        String question = questionAsString.substring(0, questionAsString.indexOf("1."));
        List<String> answers = new ArrayList<>();
        String answer1 = questionAsString.substring(questionAsString.indexOf("1.") + 2, questionAsString.indexOf("2.")).trim();
        answers.add(answer1);

        if (questionAsString.contains("3.")) {
            answers.add(questionAsString.substring(questionAsString.indexOf("2.") + 2, questionAsString.lastIndexOf("3.")).trim());

            if (questionAsString.contains("4.")) {
                answers.add(questionAsString.substring(questionAsString.indexOf("3.") + 2, questionAsString.lastIndexOf("4.")).trim());

                if (questionAsString.contains("5.")) {
                    answers.add(questionAsString.substring(questionAsString.indexOf("4.") + 2, questionAsString.lastIndexOf("5.")).trim());
                    answers.add(questionAsString.substring(questionAsString.indexOf("5.") + 2, questionAsString.lastIndexOf(correctAnswerAsString)).trim());
                } else {
                    answers.add(questionAsString.substring(questionAsString.indexOf("4.") + 2, questionAsString.lastIndexOf(correctAnswerAsString)).trim());
                }
            } else {
                answers.add(questionAsString.substring(questionAsString.indexOf("3.") + 2, questionAsString.lastIndexOf(correctAnswerAsString)).trim());
            }

        } else {
            answers.add(questionAsString.substring(questionAsString.indexOf("2.") + 2, questionAsString.lastIndexOf(correctAnswerAsString)).trim());
        }

        questionObj = new Question(index + 1, question, answers, correctAnswer, "");
        return questionObj;
    }


    public static List<String> makeNormalPdf(String fileName) {
        String content = Service.readNormalPdfAndSaveImages(fileName);
        String[] splitContent = content.split("\n");
        String backwardSplitContent = "";
        for (int i = splitContent.length - 1; i >= 0; i--) {
            backwardSplitContent += splitContent[i] + "\n";
        }
        List<String> splitContent2 = Arrays.stream(backwardSplitContent.split("Պատ․՝")).collect(Collectors.toList());
        Collections.reverse(splitContent2);
        List<String> list = new ArrayList<>();
        for (String a : splitContent2) {
            String[] strings = a.split("\n");
            String asd = "";
            for (int i = strings.length - 1; i >= 0; i--) {
                asd += strings[i] + "\n";
            }
            list.add(asd);
        }

        return list;
    }
}
