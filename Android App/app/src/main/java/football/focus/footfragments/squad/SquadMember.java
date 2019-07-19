package football.focus.footfragments.squad;

public class SquadMember
{

    private String name;
    private String pos;
    private int pic;
    private String imgurl;

    public SquadMember(String name, String pos, int pic, String imgurl) {
        this.name = name;
        this.pos = pos;
        this.pic = pic;
        this.imgurl = imgurl;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getImgurl() {return imgurl;}

    public void setImgurl(String imgurl) {this.imgurl = imgurl;}
}
