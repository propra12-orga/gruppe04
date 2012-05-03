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

	/**
	 * 
	 * @return true if field is blocked
	 */
	public boolean isBlocked() {
		return blocked;
	}

	/**
	 * Sets blocked state of field
	 * 
	 * @param blocked
	 *            set true for the field to be blocked
	 */
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	/**
	 * 
	 * @return true if block is destroyable
	 */
	public boolean isDestroyable() {
		return destroyable;
	}

	/**
	 * 
	 * @param destroyable
	 *            set true for the field to be destroyable
	 */
	public void setDestroyable(boolean destroyable) {
		this.destroyable = destroyable;
	}

	/**
	 * 
	 * @return item type of field
	 */
	public int getItem() {
		return item;
	}

	/**
	 * 0 for no item 1 2 3
	 * 
	 * @param item
	 *            set item type for field
	 */
	public void setItem(int item) {
		this.item = item;
	}

}
