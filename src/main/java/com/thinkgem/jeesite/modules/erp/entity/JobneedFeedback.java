/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.erp.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 反馈招聘需求Entity
 * @author zhuangyan
 * @version 2016-07-10
 */
public class JobneedFeedback extends DataEntity<JobneedFeedback> {
	
	private static final long serialVersionUID = 1L;
	private CompanyJobneed joobneed;		// 招聘需求ID
	private Integer feedbacktype;		// 回馈类型
	private Date feedbacktime;		// 回馈时间
	private Date beginFeedbacktime;		// 开始 回馈时间
	private Date endFeedbacktime;		// 结束 回馈时间
	
	public JobneedFeedback() {
		super();
	}

	public JobneedFeedback(String id){
		super(id);
	}

	public CompanyJobneed getJoobneed() {
		return joobneed;
	}

	public void setJoobneed(CompanyJobneed joobneed) {
		this.joobneed = joobneed;
	}
	
	@NotNull(message="回馈类型不能为空")
	public Integer getFeedbacktype() {
		return feedbacktype;
	}

	public void setFeedbacktype(Integer feedbacktype) {
		this.feedbacktype = feedbacktype;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="回馈时间不能为空")
	public Date getFeedbacktime() {
		return feedbacktime;
	}

	public void setFeedbacktime(Date feedbacktime) {
		this.feedbacktime = feedbacktime;
	}
	
	public Date getBeginFeedbacktime() {
		return beginFeedbacktime;
	}

	public void setBeginFeedbacktime(Date beginFeedbacktime) {
		this.beginFeedbacktime = beginFeedbacktime;
	}
	
	public Date getEndFeedbacktime() {
		return endFeedbacktime;
	}

	public void setEndFeedbacktime(Date endFeedbacktime) {
		this.endFeedbacktime = endFeedbacktime;
	}
		
}