package is.hi.hbv501g.eduquiz.DTO;

import is.hi.hbv501g.eduquiz.Entities.Category;

public class QuizSearchDto {

    private String keyword;
    private Category category;

    public QuizSearchDto(String keyword, Category category) {
        this.keyword = keyword;
        this.category = category;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
