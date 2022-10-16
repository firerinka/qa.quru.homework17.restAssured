package qa.quru.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SingleUserResponsePojoModel {
    private UserDataResponsePojoModel data;

    public UserDataResponsePojoModel getData() {
        return data;
    }

    public void setData(UserDataResponsePojoModel data) {
        this.data = data;
    }

}
