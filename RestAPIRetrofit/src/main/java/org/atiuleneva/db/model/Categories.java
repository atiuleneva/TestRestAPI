package org.atiuleneva.db.model;

public class Categories {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column categories.id
     *
     * @mbg.generated Wed Apr 21 14:56:18 MSK 2021
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column categories.title
     *
     * @mbg.generated Wed Apr 21 14:56:18 MSK 2021
     */
    private String title;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column categories.id
     *
     * @return the value of categories.id
     *
     * @mbg.generated Wed Apr 21 14:56:18 MSK 2021
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column categories.id
     *
     * @param id the value for categories.id
     *
     * @mbg.generated Wed Apr 21 14:56:18 MSK 2021
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column categories.title
     *
     * @return the value of categories.title
     *
     * @mbg.generated Wed Apr 21 14:56:18 MSK 2021
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column categories.title
     *
     * @param title the value for categories.title
     *
     * @mbg.generated Wed Apr 21 14:56:18 MSK 2021
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }
}