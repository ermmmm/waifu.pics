package ermmmm.api;

import lombok.Getter;

public enum Type {

	sfw(SfwCategory.class),
	nsfw(NsfwCategory.class)
	;

	@Getter
	final Class<? extends Category> category;

	Type(Class<? extends Category> category) {
		this.category = category;
	}

}
