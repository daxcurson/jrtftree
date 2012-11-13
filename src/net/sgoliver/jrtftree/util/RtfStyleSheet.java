package net.sgoliver.jrtftree.util;

import net.sgoliver.jrtftree.core.RtfNodeCollection;

public class RtfStyleSheet 
{
    //Atributos Privados

    private int index = 0;
    private String name = "";
    private int type = RtfStyleSheetType.PARAGRAPH;
    private boolean additive = false;
    private int basedOn = -1;
    private int next = -1;
    private boolean autoUpdate = false;
    private boolean hidden = false;
    private int link = -1;
    private boolean locked = false;
    private boolean personal = false;
    private boolean compose = false;
    private boolean reply = false;
    private int styrsid = -1;
    private boolean semiHidden = false;
    private RtfNodeCollection keyCode = null;
    private RtfNodeCollection formatting = null;
    
	public void setIndex(int index) {
		this.index = index;
	}
	public int getIndex() {
		return index;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}
	public void setAdditive(boolean additive) {
		this.additive = additive;
	}
	public boolean isAdditive() {
		return additive;
	}
	public void setBasedOn(int basedOn) {
		this.basedOn = basedOn;
	}
	public int getBasedOn() {
		return basedOn;
	}
	public void setNext(int next) {
		this.next = next;
	}
	public int getNext() {
		return next;
	}
	public void setAutoUpdate(boolean autoUpdate) {
		this.autoUpdate = autoUpdate;
	}
	public boolean isAutoUpdate() {
		return autoUpdate;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	public boolean isHidden() {
		return hidden;
	}
	public void setLink(int link) {
		this.link = link;
	}
	public int getLink() {
		return link;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setPersonal(boolean personal) {
		this.personal = personal;
	}
	public boolean isPersonal() {
		return personal;
	}
	public void setCompose(boolean compose) {
		this.compose = compose;
	}
	public boolean isCompose() {
		return compose;
	}
	public void setReply(boolean reply) {
		this.reply = reply;
	}
	public boolean isReply() {
		return reply;
	}
	public void setStyrsid(int styrsid) {
		this.styrsid = styrsid;
	}
	public int getStyrsid() {
		return styrsid;
	}
	public void setSemiHidden(boolean semiHidden) {
		this.semiHidden = semiHidden;
	}
	public boolean isSemiHidden() {
		return semiHidden;
	}
	public void setKeyCode(RtfNodeCollection keyCode) {
		this.keyCode = keyCode;
	}
	public RtfNodeCollection getKeyCode() {
		return keyCode;
	}
	public void setFormatting(RtfNodeCollection formatting) {
		this.formatting = formatting;
	}
	public RtfNodeCollection getFormatting() {
		return formatting;
	}
	
	/**
	 * COnstructor de la clase RtfStyleSheet.
	 */
	public RtfStyleSheet()
    {
        keyCode = null;
        formatting = new RtfNodeCollection();
    }
}
