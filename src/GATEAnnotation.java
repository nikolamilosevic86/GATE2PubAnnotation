
public class GATEAnnotation {
	private int start;
	private int end;
	private String AnnotationName;
	private String id; 
	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}
	/**
	 * @return the end
	 */
	public int getEnd() {
		return end;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(int end) {
		this.end = end;
	}
	/**
	 * @return the annotationName
	 */
	public String getAnnotationName() {
		return AnnotationName;
	}
	/**
	 * @param annotationName the annotationName to set
	 */
	public void setAnnotationName(String annotationName) {
		AnnotationName = annotationName;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

}
