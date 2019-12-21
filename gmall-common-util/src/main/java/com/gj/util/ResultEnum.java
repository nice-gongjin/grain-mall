package com.gj.util;

/**
 * 返回结果的枚举实体类
 */
public enum ResultEnum {
    NO_IMPLEMENT(-1, "接口尚未实现"),

    SUCCESS(0, "成功"),

    ERROR(500, "服务器异常，请联系管理员"),
    REDIS_ERROR(501, "Redis服务异常"),
    WORKFLOW_ERROR(502, "工作流服务器异常，请联系管理员"),

    DATA_ALREADY_EXIST(100, "数据库中已存在该记录"),

    DATA_NOT_EXIST(101, "数据不存在"),
    DELETE_ERROR(102, "删除失败"),
    ADD_ERROR(103, "添加失败"),
    UPDATE_ERROR(104, "修改失败"),
    MANAGE_ERROR(105, "操作失败"),
    RESELECT_FILE(106, "请重新选择上传文件"),
    SUBMIT_ERROR(99, "审核失败"),
    ADD_LEAVE_ERROR(131, "非工作时间、节假日、公休日不可提交请假申请"),
    ADD_OUT_ERROR(132, "非工作时间、节假日、公休日不可提交外出申请"),
    ADD_OVERTIME_ERROR(133, "工作时间不可提交加班申请"),
    TOTAL_OVERTIME_ERROR(134, "加班时长不可超过24小时"),
    DATA_EXIST_NO(135, "该条记录不存在"),
    TOTAL_TIME_ERROR(136, "申请时长以半小时为单位"),
    TOTAL_HOLIDAYTIME_ERROR(137, "假期或补班时长以半小时为单位"),
    DATA_APPROVE_YES(138, "该记录已审核"),

    USERNAME_PASSWORD_ERROR(106, "账号或密码错误"),
    USER_LOCKED(107, "账号已被锁定,请联系管理员"),
    USER_AUTH_ERROR(108, "账户验证失败"),
    NO_PERMISSION(109, "没有权限，请联系管理员授权"),
    OLD_PASSWORD_MISTAKE(110, "原密码不正确"),
    ADMIN_CANT_DELETE(111, "系统管理员不能删除"),
    USER_CANT_DELETE(112, "当前用户不能删除"),
    USER_NOT_EXIST(114, "用户不存在"),
    USER_FORBID(115, "该用户账号被禁用"),
    USER_DELETED(116, "账号被删除"),
    USER_EXIST(117, "用户已存在"),
    USER_PWD_ERROR(118, "新密码与确认密码不相同"),
    UPDATE_PWD_FAIL(119, "修改密码失败"),
    ACCOUNT_NOT_EXIST(120, "账号不存在"),
    USERNAME_NULL(121, "请输入用户名"),
    PASSWORD_NULL(122, "请输入密码"),
    RESTRICT_LOGIN(123, "连续五次密码输入错误,请一小时后再登录"),
    USER_LEAVE_OFFICE(124, "该用户已离职，无登录权限"),
    USER_LEAVE_OFFICE_FORGET(125, "该用户已离职，无修改权限"),
    LEAVE_LESS_THAN_ENTRY(125, "离职时间不可小于入职时间！"),
    USER_IS_LEAVED(126, "该员工已离职，不可重复添加！"),
    NAME_REPEAT(300, "名称重复"),
    IS_BLANK(301, "所填数据不能为空"),
    REPETITION(302, "请检查插入的数据是否有重复"),
    TIME_SAME(303, "有时间交叉"),
    OUTERS_CONTAINS_APPLYER(304, "外出人员必须包含申请人"),
    TODO_APPROVE_YES(305, "该申请已被审批"),
    EXIST_APPROVE_DATE(306,"该月还有考勤数据未审批完成，请等待审批完成后再进行设置"),
    IS_SUMMARY_STATUS(307,"该月数据已进入汇总状态，不可进行添加、修改或删除操作"),
    DONE_SUMMARY_STATUS(308,"该月数据已完成汇总，不可进行添加、修改或删除操作"),
    IS_NEGATIVE_SUMMARY(309,"统计数据不可为负数！！"),

    SENSITIVE_WORD(599, "您输入的信息中包含了违禁信息"),
    INVALID_INPUT(600, "包含非法字符"),
    PARAM_TYPE_ERROR(601, "参数类型错误"),
    PARAM_BLANK(602, "参数为空"),
    PARAM_NULL(603, "参数NULL"),
    GET_PARAM_ERROR(604, "获取参数失败"),
    VALIDATOR_NOT_PASS(605, "校验未通过"),
    API_ONLY_MAP(606, "数据权限接口，只能是Map类型参数，且不能为NULL"),



    VALIDATOR_CODE_MISTAKE(664, "验证码不正确"),
    VALIDATOR_CODE_SEND_ERROR(665, "发送验证码失败"),
    VALIDATOR_CODE_INVALID(666, "验证码已失效"),

    MENU_NOT_NULL(700, "菜单名称不能为空"),
    PARENT_MENU_NOT_NULL(701, "上级菜单不能为空"),
    MENU_URL_NOT_NULL(702, "菜单URL不能为空"),
    PARENT_MENU_ONLY_CATALOG(702, "上级菜单只能为目录类型"),
    PARENT_MENU_ONLY_MENU(703, "上级菜单只能为菜单类型"),
    SYSTEM_MENU_CANT_DELETE(704, "系统菜单，不能删除"),
    DELETE_CHILD_OR_BUTTON_FIRST(705, "请先删除子菜单或按钮"),
    SYSTEM_COLUMN_CANT_UPDATE(706, "系统的栏目名称不能修改"),
    SYSTEM_COLUMN_CANT_DELETE(707, "所选的数据中包含了系统的栏目，不能删除"),
    DELETE_CHILD_DEPARTMENT_FIRST(710, "请先删除子部门"),
    DELETE_CHILD_CATEGORY_FIRST(711, "请先删除子产品"),
    DELETE_CHILD_BASE_FIRST(712, "请先删除子类"),
    CHANGE_FAILED(713, "更改失败，请重新选择分类"),
    BEOCCUPIED(714, "该部门在其它界面被使用，不能删除"),
    SCHEDULE_JOB_RUN_ERROR(800, "执行定时任务失败"),
    SCHEDULE_JOB_CRON_ERROR(801, "获取定时任务CronTrigger出现异常"),
    SCHEDULE_JOB_CREATE_ERROR(802, "创建定时任务失败"),
    SCHEDULE_JOB_UPDATE_ERROR(803, "更新定时任务失败"),
    SCHEDULE_JOB_RUN_NOW_ERROR(804, "立即执行定时任务失败"),
    SCHEDULE_JOB_PAUSE_ERROR(805, "暂停定时任务失败"),
    SCHEDULE_JOB_RESUME_ERROR(806, "恢复定时任务失败"),
    SCHEDULE_JOB_DELETE_ERROR(807, "删除定时任务失败"),

    UPLOAD_ERROR(900, "上传文件失败"),
    UPLOAD_ERROR_CHECK_CONFIG(901, "上传文件失败，请检查配置信息"),
    UPLOAD_ERROR_CHECK_QI_NIU(902, "上传文件失败，请核对七牛配置信息"),
    UPLOAD_FILE_NOT_NULL(903, "上传文件不能为空"),
    CHOOSE_FILE(904, "请选择文件"),
    UPLOAD_DIR_NOT_EXIST(905, "上传目录不存在"),
    UPLOAD_DIR_ERROR(906, "文件类型错误"),
    UPLOAD_FILE_SIZE_LIMIT(907, "上传文件大小超过限制"),
    DOWNLOAD_ERROR(908, "请先选择月份，再导出数据"),
    INIT_ATTENDANCE_ERROR(909, "请先导入该月考勤表"),

    /* token */
    AUTH_TOKEN_INVALID(1000, "token失效，请重新登录"),
    AUTH_TOKEN_NULL(1001, "token不能为空"),

    LOCATION_NULL(2000, "未获取到您的当前位置"),

    DATA_NOT_YOURS(2100, "此公厕不是您共享的，不能修改"),
    DATA_NOT_YOURS_DEL(2101, "此公厕不是您共享的，不能删除"),
    DATA_DEL_ERROR_STATUS(2102, "此公厕不处于审核通过状态，不能删除"),

    TOKEN_USERNAME_IS_NOT_EXIT(3000, "访问令牌异常或访问令牌已过期,请重新登录"),
    ERRO(30002, "导入内容有重复，请检查手机号，身份证等信息"),
    TOKEN_IS_NULL(3001, "您尚未登录,请登录后在操作"),

    /* excel导入表 */
    EXCEL_IS_ERROR(4001, "Excel表格格式错误"),
    EXCEL_CONTENT_ERROR(4002, "标题列是必须的，请检查文件中是否包含标题列"),
    EXCEL_MUST_FILL(4003, "必填字段为空，请仔细检查"),

    FINAL_ERROR(999999999, "终极错误");


    private final Integer code;
    private final String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
