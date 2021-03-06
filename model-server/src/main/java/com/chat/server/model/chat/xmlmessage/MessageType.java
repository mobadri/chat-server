//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.02.29 at 11:32:06 AM EET 
//


package com.chat.server.model.chat.xmlmessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for messageType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="messageType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="messageContent" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="style" type="{}styleType"/>
 *         &lt;element name="userFrom" type="{}userFromType"/>
 *         &lt;element name="chatGroup" type="{}chatGroupType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "messageType", propOrder = {
    "id",
    "messageContent",
    "style",
    "userFrom",
    "chatGroup"
})
public class MessageType {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected String messageContent;
    @XmlElement(required = true)
    protected StyleType style;
    @XmlElement(required = true)
    protected UserFromType userFrom;
    @XmlElement(required = true)
    protected ChatGroupType chatGroup;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the messageContent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * Sets the value of the messageContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageContent(String value) {
        this.messageContent = value;
    }

    /**
     * Gets the value of the style property.
     * 
     * @return
     *     possible object is
     *     {@link StyleType }
     *     
     */
    public StyleType getStyle() {
        return style;
    }

    /**
     * Sets the value of the style property.
     * 
     * @param value
     *     allowed object is
     *     {@link StyleType }
     *     
     */
    public void setStyle(StyleType value) {
        this.style = value;
    }

    /**
     * Gets the value of the userFrom property.
     * 
     * @return
     *     possible object is
     *     {@link UserFromType }
     *     
     */
    public UserFromType getUserFrom() {
        return userFrom;
    }

    /**
     * Sets the value of the userFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserFromType }
     *     
     */
    public void setUserFrom(UserFromType value) {
        this.userFrom = value;
    }

    /**
     * Gets the value of the chatGroup property.
     * 
     * @return
     *     possible object is
     *     {@link ChatGroupType }
     *     
     */
    public ChatGroupType getChatGroup() {
        return chatGroup;
    }

    /**
     * Sets the value of the chatGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChatGroupType }
     *     
     */
    public void setChatGroup(ChatGroupType value) {
        this.chatGroup = value;
    }

}
