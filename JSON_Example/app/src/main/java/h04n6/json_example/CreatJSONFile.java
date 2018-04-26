package h04n6.json_example;

/**
 * Created by hoang on 3/3/2018.
 */

public class CreatJSONFile {

    //TODO khi muốn thêm nhiều part nhớ kèm dấu phẩy giữa các part

    public static String createPart1(Part1[] part1s){
        StringBuilder sb = new StringBuilder();
        sb.append("\"part1\":[");
        for(int i = 0; i < part1s.length; i++){
            sb.append("{");
            sb.append("\"id\":").append(String.valueOf(part1s[i].getId())).append(",");
            sb.append("\"sound\":\"").append(part1s[i].getSound()).append("\",");
            sb.append("\"question\":\"").append(part1s[i].getQuestion()).append("\",");
            sb.append("\"correct\":\"").append(part1s[i].getCorrect()).append("\"");
            if(i==part1s.length-1){
                sb.append("}]");
            }else{
                sb.append("},");
            }
        }
        return sb.toString();
    }

    public static String createPart2(Part2[] part2s){
        StringBuilder sb = new StringBuilder();
        sb.append("\"part2\":[");
        for(int i = 0; i < part2s.length; i++){
            sb.append("{");
            sb.append("\"id\":").append(String.valueOf(part2s[i].getId())).append(",");
            sb.append("\"question\":\"").append(part2s[i].getQuestion()).append("\",");
            sb.append("\"correct\":\"").append(part2s[i].getCorrect()).append("\"");
            if(i==part2s.length-1){
                sb.append("}]");
            }else{
                sb.append("},");
            }
        }
        return sb.toString();
    }

    //TODO part3 và part4 cấu trúc giống nhau
    public static String createPart3or4(Part3[] part3s, String partName){
        StringBuilder sb = new StringBuilder();
        sb.append("\"").append(partName).append("\":[");
        for(int i = 0; i < part3s.length; i++){
            int[] ids = part3s[i].getId();
            String[] questions = part3s[i].getQuestions();
            String[] answers = part3s[i].getAnswers();
            String[] corrects = part3s[i].getCorrects();
            sb.append("{");
            sb.append("\"id\":[");
            for(int k = 0; k < 3; k++){
                sb.append(String.valueOf(ids[k]));
                if(k < 2) sb.append(",");
                else sb.append("],");
            }
            sb.append("\"sound\":\"").append(part3s[i].getSound()).append("\",");
            sb.append("\"question\":[");
            for(int k = 0; k < 3; k++){
                sb.append(questions[k]);
                if(k < 2) sb.append(",");
                else sb.append("],");
            }
            sb.append("\"answer\":[");
            for(int k = 0; k < 12; k++){
                sb.append(answers[k]);
                if(k < 11) sb.append(",");
                else sb.append("],");
            }
            sb.append("\"correct\":[");
            for(int k = 0; k < 3; k++){
                sb.append(corrects[k]);
                if(k < 2) sb.append(",");
                else sb.append("]");
            }
            sb.append("}");

            if(i==part3s.length-1){
                sb.append("}]");
            }else{
                sb.append("},");
            }
        }
        return sb.toString();
    }


    public static String createPart5(Part5[] part5s){
        StringBuilder sb = new StringBuilder();
        sb.append("\"part5\":[");
        for(int i = 0; i < part5s.length; i++){
            String[] answers = part5s[i].getAnswers();
            sb.append("{");
            sb.append("\"id\":").append(String.valueOf(part5s[i].getId())).append(",");
            sb.append("\"question\":\"").append(part5s[i].getQuestion()).append("\",");
            sb.append("\"answer\":[");
            for(int k = 0; k < 4; k++){
                sb.append(answers[k]);
                if(k < 4) sb.append(",");
                else sb.append("],");
            }
            sb.append("\"correct\":\"").append(part5s[i].getCorrect()).append("\"");
            if(i==part5s.length-1){
                sb.append("}]");
            }else{
                sb.append("},");
            }
        }
        return sb.toString();
    }
}
