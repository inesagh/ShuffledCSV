package com.policequestions;

import com.creatingpdf.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PdfToJSONService {
    public static String pdfsToJSON(List<String> filesName) {
        Root root = new Root();
        List<Group> groups = new ArrayList<>();
        filesName.forEach(each -> {
            Group group = new Group(filesName.indexOf(each) + 1, "Группа " + (filesName.indexOf(each) + 1), pdfToJSON(each, filesName.indexOf(each) + 1));
            groups.add(group);
        });
        root.setGroups(groups);
        ObjectMapper objectMapper = new ObjectMapper();
        String result = "";
        try {
            result = objectMapper.writeValueAsString(root);
            objectMapper.writeValue(new File("question_ru1.json"), result);
            System.out.println(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Question> pdfToJSON(String fileName, int index) {
        System.out.println("=========" + fileName);
        List<String> pdf = makeNormalPdf(fileName, index);
        List<Question> questions = new ArrayList<>();
        pdf.forEach(each -> {
            if (fileName.equals("8_hy.pdf") && pdf.indexOf(each) == 67) {
            } else {
                Question question = toClass(each, pdf.indexOf(each), index);
                questions.add(question);
                System.out.println(question);
            }

        });
        return questions;
    }

    private static Question toClass(String questionAsString, int index, int groupIndex) {
        questionAsString = questionAsString.trim();
        questionAsString = questionAsString.startsWith("\n") ? questionAsString.replaceFirst("\n", "") :
                questionAsString;
        if (Character.isDigit(questionAsString.charAt(0))) {
            questionAsString = questionAsString.replaceFirst(questionAsString.charAt(0) + "", "");
        }
        if (Character.isDigit(questionAsString.charAt(0))) {
            questionAsString = questionAsString.replaceFirst(questionAsString.charAt(0) + "", "");
        }
        questionAsString = questionAsString.trim();
        Question questionObj = null;
        String[] split = questionAsString.split("\n");
        String correctAnswerAsString = split[split.length - 1].replace("\r", "").trim();
        int correctAnswer = Integer.parseInt(correctAnswerAsString);
        String question = questionAsString.substring(0, questionAsString.indexOf("1."));
        questionAsString = questionAsString.replace(question, "");
        System.out.println(questionAsString);
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

        questionObj = new Question(index + 1, question, answers, correctAnswer, groupIndex + "_" + (index + 1) + ".png");
        return questionObj;
    }


    public static List<String> makeNormalPdf(String fileName, int index) {
        String content = Service.readNormalPdfAndSaveImages(fileName, index);
        String[] splitContent = content.split("\n");
        String backwardSplitContent = "";
        for (int i = splitContent.length - 1; i >= 0; i--) {
            backwardSplitContent += splitContent[i] + "\n";
        }
        List<String> splitContent2 = Arrays.stream(backwardSplitContent.split(content.contains("Отв.") ? "Отв." : "отв․՝")).collect(Collectors.toList());
        List<String> withoutPageSplitContentList = new ArrayList<>();

        splitContent2.forEach(each -> {
            int count = 0;
            for (int i = 0; i < each.length(); i++) {
                if (Character.isLetter(each.charAt(i))) {
                    count++;
                }
            }
            if (count != 0) {
                withoutPageSplitContentList.add(each);
            }
        });

        Collections.reverse(withoutPageSplitContentList);
        List<String> list = new ArrayList<>();
        for (String a : withoutPageSplitContentList) {
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
