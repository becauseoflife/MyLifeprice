package com.example.myapplication.data;

import androidx.test.core.app.ApplicationProvider;

import com.example.myapplication.R;
import com.example.myapplication.data.model.Good;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileDataSourceTest {

    private FileDataSource keeper;
    @Before
    public void setUp() throws Exception {
        keeper = new FileDataSource(ApplicationProvider.getApplicationContext());
        keeper.load();
    }

    @After
    public void tearDown() throws Exception {
        keeper.save();
    }

    @Test
    public void getGoods() {
        FileDataSource fileDataSource  = new FileDataSource(ApplicationProvider.getApplicationContext());
        Assert.assertEquals(0, fileDataSource.getGoods().size());
    }

    @Test
    public void save() {
        FileDataSource fileDataSource = new FileDataSource(ApplicationProvider.getApplicationContext());
        Assert.assertEquals(0, fileDataSource.getGoods().size());
        fileDataSource.getGoods().add(new Good("测试1", 1.23, R.drawable.a4));
        fileDataSource.getGoods().add(new Good("测试2", 2.22, R.drawable.a3));
        fileDataSource.save();
        FileDataSource fileLoder = new FileDataSource(ApplicationProvider.getApplicationContext());
        fileLoder.load();

        Assert.assertNotEquals(fileDataSource.getGoods(), fileLoder.getGoods());
        Assert.assertEquals(fileDataSource.getGoods().size(), fileLoder.getGoods().size());

        for (int i =0; i < fileDataSource.getGoods().size(); i++)
        {
            Good goodThis = fileDataSource.getGoods().get(i);
            Good goodThat = fileLoder.getGoods().get(i);

            Assert.assertNotEquals(goodThis, goodThat);
            Assert.assertEquals(goodThis.getName(), goodThat.getName());
            Assert.assertEquals(goodThis.getPrice(), goodThat.getPrice(), 1e-6);

        }

    }

    @Test
    public void saveEmptyThenLoad() {
        FileDataSource fileDataSource = new FileDataSource(ApplicationProvider.getApplicationContext());
        Assert.assertEquals(0, fileDataSource.getGoods().size());

        fileDataSource.save();
        FileDataSource fileLoder = new FileDataSource(ApplicationProvider.getApplicationContext());
        fileLoder.load();

        //Assert.assertEquals(fileDataSource.getGoods(), fileLoder.getGoods());
        Assert.assertEquals(fileDataSource.getGoods().size(), fileLoder.getGoods().size());
    }
}