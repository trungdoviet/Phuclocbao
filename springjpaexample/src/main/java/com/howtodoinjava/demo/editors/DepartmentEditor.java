package com.howtodoinjava.demo.editors;

import java.beans.PropertyEditorSupport;

import com.howtodoinjava.demo.model.DepartmentEntity;

public class DepartmentEditor extends PropertyEditorSupport 
{
    @Override
    public void setAsText(String id) 
    {
    	DepartmentEntity d;
    	switch(Integer.parseInt(id))
		{
			case 1: d = new DepartmentEntity(1,  "Human Resource"); break;
			case 2: d = new DepartmentEntity(2,  "Finance"); break;
			case 3: d = new DepartmentEntity(3,  "Information Technology"); break;
			default: d = null;
		}
        this.setValue(d);
    }
}
