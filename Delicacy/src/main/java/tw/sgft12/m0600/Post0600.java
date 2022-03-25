package tw.sgft12.m0600;

public class Post0600 {
    public String Name;
    public String posterThumbnailUrl;
    public String Add;
    public String Zipcode;
    public String Px;
    public String Py;
    public String Region;
    public String Tel;
    public String Town;
    public String id2;
    public String Content;

    public Post0600(String Name,
                    String posterThumbnailUrl,
                    String Add,
                    String content,
                    String Zipcode,
                    String Px,
                    String Py,
                    String Region,
                    String Tel,
                    String Town,
                    String id2
    ) {

        this.Name = Name;  //名稱
        this.posterThumbnailUrl = posterThumbnailUrl; //圖片
        this.Add = Add;  //住址
        this.Zipcode = Zipcode; //郵遞區號
        this.Px = Px;
        this.Py = Py;
        this.Region = Region;
        this.Tel = Tel;
        this.Town = Town;
        this.id2 = id2;
        this.Content = content; //描述
    }
}
