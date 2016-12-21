package data_transformation;

public class PostRecord {

	private long postId;
	private String title;
	private String question;
	private String answer;
	private long classLabel;
	public long getPostId() {
		return postId;
	}
	public void setPostId(long postId) {
		this.postId = postId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public long getClassLabel() {
		return classLabel;
	}
	public void setClassLabel(long classLabel) {
		this.classLabel = classLabel;
	}
	public PostRecord(long postId, String title, String question, String answer,
			long classLabel) {
		super();
		this.postId = postId;
		this.title = title;
		this.question = question;
		this.answer = answer;
		this.classLabel = classLabel;
	}
	
	public PostRecord() {
		super();
		this.postId = 0;
		this.title = "";
		this.question = "";
		this.answer = "";
		this.classLabel = -1;
	}
	
}
