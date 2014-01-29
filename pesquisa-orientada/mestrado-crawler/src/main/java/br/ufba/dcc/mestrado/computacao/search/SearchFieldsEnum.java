package br.ufba.dcc.mestrado.computacao.search;

public enum SearchFieldsEnum {
	projectName("name", 1.5f),
	projectDescription("description", 1.4f),
	linkCategory("ohLohLinks.category", 1.0f),
	linkTitle("ohLohLinks.title", 1.0f),
	linkURL("ohLohLinks.url", 1.0f),
	tagName("ohLohTags.name", 1.3f),
	licenseName("ohLohLicenses.name", 1.2f),
	licenseNiceName("ohLohLicenses.niceName", 1.2f),
	projectMainLanguageName("ohLohAnalysis.mainLanguageName", 1.0f),
	languageName("ohLohAnalysis.ohLohAnalysisLanguages.content.ohLohLanguage.name", 1.0f),
	languageNiceName("ohLohAnalysis.ohLohAnalysisLanguages.content.ohLohLanguage.niceName", 1.0f),
	languageCategory("ohLohAnalysis.ohLohAnalysisLanguages.content.ohLohLanguage.category", 1.0f);
	
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
