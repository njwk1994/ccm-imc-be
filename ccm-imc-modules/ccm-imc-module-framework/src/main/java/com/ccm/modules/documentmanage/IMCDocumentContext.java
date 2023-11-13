package com.ccm.modules.documentmanage;

/**
 * @description：TODO
 * @author： kekai.huang
 * @create： 2023/11/7 16:10
 */
public class IMCDocumentContext {
    public static final String CLASS_CIM_DESIGN_DOC_REVISION = "CIMDesignDocRevision";

    public static final String CLASS_CIM_DOCUMENT_VERSION = "CIMDesignDocVersion";

    public static final String CLASS_CIM_REVISION_SCHEMA = "CIMRevisionScheme";

    public static final String INTERFACE_CIM_DOCUMENT_REVISION = "ICIMDocumentRevision";

    public static final String INTERFACE_CIM_DOCUMENT_VERSION = "ICIMDocumentVersion";

    public static final String INTERFACE_CIM_REVISION_ITEM = "ICIMRevisionItem";

    public static final String REL_CIM_REVISION_SCHEMA = "CIMDocumentRevisionScheme";

    public static final String REL_CIM_DOCUMENT_REVISIONS = "CIMDocumentRevisions";

    public static final String REL_CIM_REVISION_VERSIONS = "CIMDocumentRevisionVersions";

    /**
     * 完整版本
     */
    public static final String EXTERNAL_REVISION = "CIMExternalRevision";

    /**
     * 主版本
     */
    public static final String MAJOR_REVISION = "CIMMajorRevision";

    /**
     * 次版本
     */
    public static final String MINOR_REVISION = "CIMMinorRevision";

    /**
     * 版本规则
     */
    public static final String REVISION_SCHEMA = "CIMRevisionScheme";

    /**
     * 签结日期
     */
    public static final String REV_ISSUE_DATE = "CIMRevIssueDate";

    /**
     * 版本状态
     */
    public static final String REV_STATE = "CIMRevState";

    /**
     * 签结内容
     */
    public static final String SIGN_OFF_COMMENTS = "CIMSignOffComments";

    /**
     * 是否过期
     */
    public static final String IS_DOC_VERSION_SUPERSEDED = "CIMIsDocVersionSuperseded";

    /**
     * 签入日期
     */
    public static final String VERSION_CHECK_IN_DATE = "CIMVersionCheckInDate";

    /**
     * 版本状态
     */
    public static final String REVISION_ITEM_REV_STATE = "CIMRevisionItemRevState";

    /**
     * 操作状态
     */
    public static final String REVISION_ITEM_OPERATION_STATE = "CIMRevisionItemOperationState";

    /**
     * 次版本
     */
    public static final String REVISION_ITEM_MINOR_REVISION = "CIMRevisionItemMinorRevision";

    /**
     * 主版本
     */
    public static final String REVISION_ITEM_MAJOR_REVISION = "CIMRevisionItemMajorRevision";
}
