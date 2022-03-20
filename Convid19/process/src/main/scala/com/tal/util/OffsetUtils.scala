package com.tal.util


import org.apache.kafka.common.TopicPartition
import org.apache.spark.streaming.kafka010.OffsetRange

import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet}

import scala.collection.mutable

/*
    @TODO: 用来手动维护偏移量到MySQL中的工具类
    @Author tal
*/

object OffsetUtils {
  /**
   * 根据参数查询偏移量信息并封装成Map返回
   * @param groupId 消费者组名称
   * @param topic   主题
   * @return        偏移量信息封装成的Map
   */

  def getOffsetsMap(groupId: String, topic: String): mutable.Map[TopicPartition, Long] = {
    //1.获取连接
    val conn: Connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigdata1?characterEncoding=UTF-8","root","root")
    //2.编写sql
    val sql:String = "select par, offset from t_offset where groupid = ? and topic = ?"
    //3.获取ps
    val ps: PreparedStatement = conn.prepareStatement(sql)
    //4.设置参数并执行
    ps.setString(1,groupId)
    ps.setString(2,topic)
    val rs: ResultSet = ps.executeQuery()
    //5.获取返回值并封装成Map
    val offsetsMap:mutable.Map[TopicPartition, Long] = mutable.Map[TopicPartition, Long]()
    while(rs.next()){
      val partition: Int = rs.getInt("par")
      val offset: Int = rs.getInt("offset")
      offsetsMap += new TopicPartition(topic,partition) -> offset
    }

    //6.关闭资源
    rs.close()
    ps.close()
    conn.close()
    //7.返回Map
    offsetsMap
  }

  /**
   * 将消费者组的偏移量信息存入到MySQL
   * @param groupId 消费者组名称
   * @param offsets 偏移量信息
   */

  /*
  CREATE TABLE `t_offset` (
    `topic` varchar(255) NOT NULL,
    `partition` int(11) NOT NULL,
    `groupid` varchar(255) NOT NULL,
    `offset` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`topic`,`partition`,`groupid`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  */
  def saveOffsets(groupId: String, offsets: Array[OffsetRange]):Unit = {
    //1.获取连接
    val conn: Connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigdata1?characterEncoding=UTF-8","root","root")
    //2.编写sql
    val sql:String = "replace into t_offset (topic, partition, groupid, offset) values(?,?,?,?)"
    //3.创建预编译语句对象
    val ps: PreparedStatement = conn.prepareStatement(sql)
    //4.设置参数并执行
    for(o <- offsets){
      ps.setString(1,o.topic)
      ps.setInt(2,o.partition)
      ps.setString(3,groupId)
      ps.setLong(4,o.untilOffset)   //消费到哪，下次继续
      ps.executeUpdate()
    }
    //5.关闭资源
    ps.close()
    conn.close()

  }

}
