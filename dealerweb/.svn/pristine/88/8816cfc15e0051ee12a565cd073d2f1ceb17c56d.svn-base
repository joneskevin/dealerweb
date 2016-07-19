package com.ava.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.dao.ITestNoDriveWeekDao;
import com.ava.domain.entity.Box;
import com.ava.domain.entity.TestNoDriveWeek;

@Repository
public class TestNoDriveWeekDao implements ITestNoDriveWeekDao {
	@Autowired
	private IHibernateDao hibernateDao;

	public Integer save(TestNoDriveWeek testNoDriveWeek) {
		Integer objId = null;
		if (testNoDriveWeek != null) {
			try {
				objId = (Integer) hibernateDao.save(testNoDriveWeek);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return objId;
	}

	public void delete(Integer id) {
		hibernateDao.delete(TestNoDriveWeek.class, id);
	}

	public void update(TestNoDriveWeek testNoDriveWeek) {
		hibernateDao.update(testNoDriveWeek);
	}

	@Override
	public TestNoDriveWeek get(Integer id) {
		if (id == null) {
			return null;
		}
		TestNoDriveWeek testNoDriveWeek = (TestNoDriveWeek) hibernateDao.get(TestNoDriveWeek.class, id);
		return testNoDriveWeek;
	}
}
