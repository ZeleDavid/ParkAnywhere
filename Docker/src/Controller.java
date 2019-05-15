import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named(value="Controller")
@SessionScoped
public class Controller implements Serializable {
	private String testniNiz = "DAVIDJENUB";

	public String getTestniNiz() {
		return testniNiz;
	}

	public void setTestniNiz(String testniNiz) {
		this.testniNiz = testniNiz;
	}
}
