package org.tsir.common.services.resources.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * ResourceDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-08-17T00:37:58.511Z[GMT]")
@JacksonXmlRootElement(localName = "ResourceDTO")
@XmlRootElement(name = "ResourceDTO")
@XmlAccessorType(XmlAccessType.FIELD)

public class ResourceDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	@JacksonXmlProperty(localName = "id")
	private Long id = null;

	@JsonProperty("code")
	@JacksonXmlProperty(localName = "code")
	private Integer code = null;

	@JsonProperty("label")
	@JacksonXmlProperty(localName = "label")
	private String label = null;

	@JsonProperty("active")
	@JacksonXmlProperty(localName = "active")
	private StateActive active = null;

	@JsonProperty("path")
	@JacksonXmlProperty(localName = "path")
	private String path = null;

	@JsonProperty("location")
	@JacksonXmlProperty(localName = "location")
	private String location = null;

	@JsonProperty("icon")
	@JacksonXmlProperty(localName = "icon")
	private String icon = null;

	@JsonProperty("type")
	@JacksonXmlProperty(localName = "type")
	private ResourceType type = null;

	@JsonProperty("parent")
	@JacksonXmlProperty(localName = "parent")
	private Long parent = null;

	public ResourceDTO id(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * Identificador de la opción.
	 * 
	 * @return id
	 **/
	@Schema(description = "Identificador de la opción.")

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ResourceDTO code(Integer code) {
		this.code = code;
		return this;
	}

	/**
	 * Código de la opción en el modulo general.
	 * 
	 * @return code
	 **/
	@Schema(description = "Código de la opción en el modulo general.")

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public ResourceDTO label(String label) {
		this.label = label;
		return this;
	}

	/**
	 * Nombre de la opción.
	 * 
	 * @return label
	 **/
	@Schema(description = "Nombre de la opción.")

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ResourceDTO active(StateActive active) {
		this.active = active;
		return this;
	}

	/**
	 * Get active
	 * 
	 * @return active
	 **/
	@Schema(description = "")

	@Valid
	public StateActive getActive() {
		return active;
	}

	public void setActive(StateActive active) {
		this.active = active;
	}

	public ResourceDTO path(String path) {
		this.path = path;
		return this;
	}

	/**
	 * Ruta de acceso al modulo visual asociada al recurso.
	 * 
	 * @return path
	 **/
	@Schema(description = "Ruta de acceso al modulo visual asociada al recurso.")

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ResourceDTO location(String location) {
		this.location = location;
		return this;
	}

	/**
	 * URL de acceso a los recursos que son tipo módulo.
	 * 
	 * @return location
	 **/
	@Schema(description = "URL de acceso a los recursos que son tipo módulo.")

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public ResourceDTO icon(String icon) {
		this.icon = icon;
		return this;
	}

	/**
	 * Nombre o ubicación del icono asignado a la opción visual.
	 * 
	 * @return icon
	 **/
	@Schema(description = "Nombre o ubicación del icono asignado a la opción visual.")

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public ResourceDTO type(ResourceType type) {
		this.type = type;
		return this;
	}

	/**
	 * Get type
	 * 
	 * @return type
	 **/
	@Schema(description = "")

	@Valid
	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public ResourceDTO parent(Long parent) {
		this.parent = parent;
		return this;
	}

	/**
	 * Identificador del recurso padre al que pertenece.
	 * 
	 * @return parent
	 **/
	@Schema(description = "Identificador del recurso padre al que pertenece.")

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ResourceDTO resourceDTO = (ResourceDTO) o;
		return Objects.equals(this.id, resourceDTO.id) && Objects.equals(this.code, resourceDTO.code)
				&& Objects.equals(this.label, resourceDTO.label) && Objects.equals(this.active, resourceDTO.active)
				&& Objects.equals(this.path, resourceDTO.path) && Objects.equals(this.location, resourceDTO.location)
				&& Objects.equals(this.icon, resourceDTO.icon) && Objects.equals(this.type, resourceDTO.type)
				&& Objects.equals(this.parent, resourceDTO.parent);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, code, label, active, path, location, icon, type, parent);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ResourceDTO {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    code: ").append(toIndentedString(code)).append("\n");
		sb.append("    label: ").append(toIndentedString(label)).append("\n");
		sb.append("    active: ").append(toIndentedString(active)).append("\n");
		sb.append("    path: ").append(toIndentedString(path)).append("\n");
		sb.append("    location: ").append(toIndentedString(location)).append("\n");
		sb.append("    icon: ").append(toIndentedString(icon)).append("\n");
		sb.append("    type: ").append(toIndentedString(type)).append("\n");
		sb.append("    parent: ").append(toIndentedString(parent)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
