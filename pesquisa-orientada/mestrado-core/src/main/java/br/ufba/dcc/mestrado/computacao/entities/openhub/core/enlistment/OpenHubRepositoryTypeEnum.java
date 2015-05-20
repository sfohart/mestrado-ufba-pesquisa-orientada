package br.ufba.dcc.mestrado.computacao.entities.openhub.core.enlistment;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(String.class)
public enum OpenHubRepositoryTypeEnum {
	@XmlEnumValue("SvnRepository") SvnRepository, 
	@XmlEnumValue("CvsRepository") CvsRepository, 
	@XmlEnumValue("GitRepository") GitRepository, 
	@XmlEnumValue("HgRepository") HgRepository, 
	@XmlEnumValue("BzrRepository") BzrRepository, 
	@XmlEnumValue("SvnSyncRepository") SvnSyncRepository;
}
