package h04n6.json_example;

/**
 * Created by hoang on 2/17/2018.
 */

public class Company {
    private int id;
    private String name;
    private String[] websites;
    private Address adress;

    public Company(){}

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String[] getWebsites(){
        return websites;
    }

    public void setWebsites(String[] websites){
        this.websites = websites;
    }

    public Address getAdress(){
        return this.adress;
    }

    public void setAdress(Address adress){
        this.adress = adress;
    }

    //append (v) : nối
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n id:" + this.id);
        sb.append("\n name:" + this.name);
        if(this.websites != null){
            sb.append("\n website: ");
            for(String website : this.websites){
                sb.append(website + ", ");
            }
        }
        if(this.adress != null){
            sb.append("\n address:" + this.adress.toString());
        }
        return sb.toString();
    }
}
