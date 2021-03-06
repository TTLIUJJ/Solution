package zk;

import java.io.IOException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import zk.common.Constant;
import zk.common.CustomWatcher;

/**
 * Created by Anur IjuoKaruKas on 2018/12/7
 */
public class TestZkClientCreate {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        //        createSync();
        createAsync();
    }

    public static void createSync() throws IOException, InterruptedException, KeeperException {
        CustomWatcher customWatcher = new CustomWatcher("create - test - sync");
        ZooKeeper zooKeeper = new ZooKeeper(Constant.CONNECT_STR, 5000, customWatcher);
        customWatcher.await();

        String path1 = zooKeeper.create("/sanguo", "luoguanzhong".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("Create path " + path1);

        String path2 = zooKeeper.create("/sanguoz", "luoguanzhong".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Create path " + path2);
    }

    public static void createAsync() throws IOException, InterruptedException, KeeperException {
        CustomWatcher customWatcher = new CustomWatcher("create - test - async");
        ZooKeeper zooKeeper = new ZooKeeper(Constant.CONNECT_STR, 5000, customWatcher);
        customWatcher.await();

        zooKeeper.create("/sanguo111", "luoguanzhong".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,
            (i, s, o, s1) -> System.out.println(String.format("create path async rc = %s, path = %s, ctx = %s, real path = %s", i, s, o, s1)), "This is context");

        zooKeeper.create("/sanguo111", "luoguanzhong".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
            (i, s, o, s1) -> System.out.println(String.format("create path async rc = %s, path = %s, ctx = %s, real path = %s", i, s, o, s1)), "This is context");

        Thread.sleep(10000);
    }
}
