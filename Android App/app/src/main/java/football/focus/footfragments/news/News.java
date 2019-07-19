package football.focus.footfragments.news;

public class News
{
    private String imgUrl;
    private String newsTitle;
    private String date;
    private String summary;

    public News( String imgUrl, String newsTitle, String date, String summary) {
        this.newsTitle = newsTitle;
        this.date = date;
        this.summary = summary;
        this.imgUrl = imgUrl;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSummary() {return summary;}

    public void setSummary(String summary) {this.summary = summary;}

    public String getImgUrl() {return imgUrl;}

    public void setImgUrl(String imgUrl) {this.imgUrl = imgUrl;}
}
