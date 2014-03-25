package br.ufba.dcc.mestrado.computacao.search;

public enum SearchFieldsEnum {
	projectName("name", 5.0f),
	projectDescription("description", 2.0f),
	projectUserCount("user_count",1.0f),
	projectRatingCount("rating_count",1.0f),
	projectReviewCount("review_count",1.0f),
	linkCategory("links.category", 1.0f),
	linkTitle("links.title", 1.0f),
	linkURL("links.url", 1.0f),
	tagName("tags.name", 3.0f),
	licenseName("licenses.name", 1.2f),
	licenseNiceName("licenses.niceName", 1.2f),
	projectMainLanguageName("analysis.mainLanguageName", 1.0f),
	languageName("analysis.analysisLanguages.content.language.name", 1.0f),
	languageNiceName("analysis.analysisLanguages.content.language.niceName", 1.0f),
	languageCategory("analysis.analysisLanguages.content.language.category", 1.0f);
	
	private Float boost;
	
	private String fieldName;
	
	private SearchFieldsEnum(String fieldName, Float boost) {
		this.boost = boost;
		this.fieldName = fieldName;
	}
	
	public String fieldName() {
		return fieldName;
	}
	
	public Float boost() {
		return boost;
	}
	
	public static String[] toArrayNames() {
		String[] array = new String[] {
			projectName.fieldName(),
			projectDescription.fieldName(),
			projectUserCount.fieldName(),
			projectRatingCount.fieldName(),
			projectReviewCount.fieldName(),
			linkCategory.fieldName(),
			linkTitle.fieldName(),
			linkURL.fieldName(),
			tagName.fieldName(),
			licenseName.fieldName(),
			licenseNiceName.fieldName(),
			projectMainLanguageName.fieldName(),
			languageName.fieldName(),
			languageNiceName.fieldName(),
			languageCategory.fieldName()
		};
		
		return array;
	}
	
}
