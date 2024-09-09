package edu.javaproject.studentorder.domain;

// Marriage Register Office Data. Все тільки про офіс реєстрації. Ай-ді зайвки тут немає
public class RegisterOffice {
    private Long officeId;
    private String officeName;
    private String officeAreaId;

    public String getOfficeAreaId() {
        return officeAreaId;
    }

    public void setOfficeAreaIdId(String officeAreaId) {
        this.officeAreaId = officeAreaId;
    }

    public RegisterOffice(Long officeId, String officeName, String officeAreaId) {
        this.officeId = officeId;
        this.officeName = officeName;
        this.officeAreaId = officeAreaId;
    }

    public RegisterOffice() {}

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

}
