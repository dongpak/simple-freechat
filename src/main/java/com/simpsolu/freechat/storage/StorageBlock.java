/**
 * 
 */
package com.simpsolu.freechat.storage;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author dongp
 *
 */
public class StorageBlock<I> extends ArrayList<I>  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private long	blockId;
	private int		blockSize;
	private long	blockTotal;
	
	private List<String>	sorts	= new ArrayList<String>();
	
	/**
	 * @return the blockId
	 */
	public long getBlockId() {
		return blockId;
	}

	/**
	 * @param blockId the blockId to set
	 */
	public void setBlockId(long blockId) {
		this.blockId = blockId;
	}

	/**
	 * @return the blockSize
	 */
	public int getBlockSize() {
		return blockSize;
	}

	/**
	 * @param blockSize the blockSize to set
	 */
	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	/**
	 * @return the blockTotal
	 */
	public long getBlockTotal() {
		return blockTotal;
	}

	/**
	 * @param blockTotal the blockTotal to set
	 */
	public void setBlockTotal(long blockTotal) {
		this.blockTotal = blockTotal;
	}

	/**
	 * @return the sorts
	 */
	public List<String> getSorts() {
		return sorts;
	}

	/**
	 * Sort criteria has the format "(+|-)field-name"
	 * 
	 * @param adds the sort criteria
	 */
	public void addSort(String sort) {
		sorts.add(sort);
	}
}