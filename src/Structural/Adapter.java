package Structural;

import Creational.*;

public class Adapter {

	private Adapter_Legacy legacy;
	
	public Adapter(Adapter_Legacy legacy) {
		this.legacy = legacy;
	}

	public Book adapt() {
		return new Book.BookBuilder(
			legacy.getOldTitle(),
			legacy.getOldAuthor(),
			legacy.getOldCode())
			.digitalAccess(false)
			.lendingRestricted(true)
			.preservationNote("Imported from legacy system")
			.build();
    }	
}
