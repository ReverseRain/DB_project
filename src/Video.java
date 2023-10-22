public class Video {
    private String BV;
    private String title;
    private String owner_mid;
    private String owner_name;
    private String commit_time;
    private String review_time;
    private String public_time;
    private String duration;
    private String description;
    private String reviewer;
    private String like_mid;
    private String coin_mid;
    private String favorite_mid;
    private String viewList;

    public Video(String BV, String title, String owner_mid, String owner_name,
                 String commit_time, String review_time, String public_time, String duration, String description,
                 String reviewer, String like_mid, String coin_mid, String favorite_mid, String viewList) {
        this.BV = BV;
        this.title = title;
        this.owner_mid = owner_mid;
        this.owner_name = owner_name;
        this.commit_time = commit_time;
        this.review_time = review_time;
        this.public_time = public_time;
        this.duration = duration;
        this.description = description;
        this.reviewer = reviewer;
        this.like_mid = like_mid;
        this.coin_mid = coin_mid;
        this.favorite_mid = favorite_mid;
        this.viewList = viewList;
    }
}
