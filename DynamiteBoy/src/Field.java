public abstract class Field {

	Field(boolean blocked, boolean destroyable, int item, Picture pic) {
		this.blocked = blocked;
		this.destroyable = destroyable;
		this.item = item;
		this.pic = pic;
	}

	private boolean blocked;
	private boolean destroyable;
	private int item;
	Picture pic;

}
