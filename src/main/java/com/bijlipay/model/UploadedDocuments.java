package com.bijlipay.model;

import com.bijlipay.model.dto.SignzyUpdateDto;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "uploaded_documents")
public class UploadedDocuments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uploaded_document_id", nullable = false)
    private long uploadedDocumentId;

    @ManyToOne(optional = true)
    @JoinColumn(name = "lead_document_id", referencedColumnName = "lead_document_id")
    private LeadDocuments leadDocuments;

    @Column(name = "file_original_name", nullable = true)
    private String fileOriginalName;

    @Column(name = "file_name", nullable = true)
    private String fileName;

    @Column(name = "mime_type", nullable = true)
    private String mimeType;

    @Column(name = "type_of_document", nullable = true)
    private String typeOfDocument;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Transient
    private SignzyUpdateDto signzyUpdateDto;

    public long getUploadedDocumentId() {
        return uploadedDocumentId;
    }

    public void setUploadedDocumentId(long uploadedDocumentId) {
        this.uploadedDocumentId = uploadedDocumentId;
    }

    public LeadDocuments getLeadDocuments() {
        return leadDocuments;
    }

    public void setLeadDocuments(LeadDocuments leadDocuments) {
        this.leadDocuments = leadDocuments;
    }

    public String getFileOriginalName() {
        return fileOriginalName;
    }

    public void setFileOriginalName(String fileOriginalName) {
        this.fileOriginalName = fileOriginalName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public SignzyUpdateDto getSignzyUpdateDto() {
        return signzyUpdateDto;
    }

    public void setSignzyUpdateDto(SignzyUpdateDto signzyUpdateDto) {
        this.signzyUpdateDto = signzyUpdateDto;
    }

    public String getTypeOfDocument() {
        return typeOfDocument;
    }

    public void setTypeOfDocument(String typeOfDocument) {
        this.typeOfDocument = typeOfDocument;
    }

    @Override
    public String toString() {
        return "UploadedDocuments{" +
                "uploadedDocumentId=" + uploadedDocumentId +
                ", leadDocuments=" + leadDocuments +
                ", fileOriginalName='" + fileOriginalName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", typeOfDocument='" + typeOfDocument + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", signzyUpdateDto=" + signzyUpdateDto +
                '}';
    }
}
