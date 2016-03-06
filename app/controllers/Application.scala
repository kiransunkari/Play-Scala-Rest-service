package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models._



object Application extends Controller {
  
  val taskForm = Form(
  mapping(
  "id" -> ignored(None:Option[Long]),
  "label" -> nonEmptyText,
  "phone" -> nonEmptyText,
  "email" -> nonEmptyText,
  "address"   -> nonEmptyText,
  "sex" -> nonEmptyText,
  "age" -> number(min = 0, max = 100)
  )(Task.apply)(Task.unapply)
)


var  GetNameForm = Form(
 mapping(
  "sex" -> nonEmptyText,
  "age" -> number(min = 0, max = 100)
 )(Details.apply)(Details.unapply)
 )
 
 
 var GetUserForm = Form(
 mapping(
 "email" ->  nonEmptyText
 )(Email.apply)(Email.unapply)
 )

def index = Action {
    Redirect(routes.Application.tasks)
}
  
def tasks = Action {
  Ok(views.html.index(Task.all(), taskForm))
}
 
  
  
 

    def newTask = Action { implicit request =>
	print(taskForm.bindFromRequest)
   taskForm.bindFromRequest.fold(
    errors => BadRequest(views.html.index(Task.all(), errors)),
    label => {
	println("Label is ")
	println(label);
      Task.create(label.label,label.phone,label.email,label.address,label.sex,label.age)
      Redirect(routes.Application.tasks)
    }
  )
}
  



def GetNameByInfo = Action{
Ok(views.html.Editpage(Task.all(),GetNameForm))
}


def UserDetailsBymail  = Action{
Ok(views.html.UserDetail(Task.all(),GetUserForm))
}


  def UpdateInfo(id: Option[Long]) = Action  { implicit request =>
   println(id)
   println("Result Data Is")
	println(taskForm.bindFromRequest)
   taskForm.bindFromRequest.fold(
    errors => BadRequest(views.html.index(Task.all(), taskForm)),
    label => {
	println("Label is ")
	println(id);
      Task.UpdateUserDetails(id,label.label,label.phone,label.email,label.address,label.sex,label.age)
      Redirect(routes.Application.tasks)
    }
  )
}
  

def UpdateUserDetails(id: Option[Long]) = Action {
  Ok(views.html.UpdatePage(Task.GetUserDetailsBYId(id),taskForm))
}



def GetUserByEmail =  Action { implicit request =>
print(GetUserForm.bindFromRequest)
  GetUserForm.bindFromRequest.fold(
    errors => BadRequest(views.html.index(Task.all(), taskForm)),
    label => { 
    Ok(views.html.UserDetail(Task.GetDetailsByEmail(label.email),GetUserForm))
  }
  )
}

def GetNameById = Action { implicit request =>
print(GetNameForm.bindFromRequest)
  GetNameForm.bindFromRequest.fold(
    errors => BadRequest(views.html.index(Task.all(), taskForm)),
    label => { 
    Ok(views.html.Editpage(Task.GetNameById(label.sex,label.age),GetNameForm))
  }
  )
}
}
