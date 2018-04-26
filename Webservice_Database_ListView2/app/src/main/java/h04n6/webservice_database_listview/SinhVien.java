package h04n6.webservice_database_listview;

/**
 * Created by hoang on 3/11/2018.
 */

public class SinhVien {
    private String image, sound, correct;

    public SinhVien(String image, String sound, String correct) {
        this.image = image;
        this.sound = sound;
        this.correct = correct;
    }

    public String getImage() {
        return image;
    }

    public String getSound() {
        return sound;
    }

    public String getCorrect() {
        return correct;
    }
}
