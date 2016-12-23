package models
import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Task(id: Option[Long] = None,label: String,email: String,phone: String,sex: String,address:String,age:Int)

case class Details(sex:String,age:Int)

case class Email(email:String)




object Task{

val task = {
 get[Option[Long]]("Task.id") ~ 
 get[String]("Task.label") ~ 
 get[String]("Task.email") ~
 get[String]("Task.phone") ~
 get[String]("Task.sex") ~
 get[String]("Task.address") ~
 get[Int]("Task.age") map {
    case (id~label~email~phone~sex~address~age) => Task(id, label,email,phone,sex,address,age)
  }
}


val details = {
get[String]("Details.sex") ~
get[Int]("Details.age") map {
     case (sex ~ age) => Details(sex,age)
	 }
}

var EmailForm = {
get[String]("Email.email") map
{
case (email) => Email(email)
}
}


def all(): List[Task] = DB.withConnection { implicit c =>
  SQL("select * from task").as(task *)
}

def create(label: String,email:String,phone:String,sex:String,address:String,age:Int) {
  DB.withConnection { implicit c =>
    SQL("insert into task (label,email,phone,address,sex,age) values ({label},{email},{phone},{address},{sex},{age})").on(
      'label -> label,
	  'email -> email,
	  'phone -> phone,
	  'address -> address,
	  'sex -> sex,
	  'age -> age
    ).executeUpdate()
  }
}


def GetNameById(sex : String,age: Int ):List[Task] = DB.withConnection { implicit c =>
 print("select * from task where age ="+({age}))
SQL("select * from task where age ={age} AND sex = {sex}").on('age -> age,'sex -> sex).as(task *)
}


def GetUserDetailsBYId(Id : Option[Long]):Task = DB.withConnection {
implicit c => SQL("select * from task where Id = {Id}").on('Id -> Id).as(task.single) 
}

def GetDetailsByEmail(email : String):List[Task] = DB.withConnection { implicit c =>
SQL("select * from task where email ={email}").on('email -> email ).as(task *)
}


def UpdateUserDetails(Id:Option[Long],label: String,email:String,phone:String,sex:String,address:String,age:Int)
{
println("In Task")
println(Id);
println(label);
println(email);
println(phone);
println(sex);
println(address);
println(age);

 DB.withConnection { implicit c =>
    SQL("update  task  set label = {label},email = {email},phone={phone},age ={age},sex = {sex},address ={address} where Id = {Id}").on(
      'label -> label,
	  'email -> email,
	  'phone -> phone,
	  'address -> address,
	  'sex -> sex,
	  'age -> age,
	  'Id -> Id
    ).executeUpdate()
  }
}
}