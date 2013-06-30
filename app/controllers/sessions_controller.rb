class SessionsController < ApplicationController

include SessionsHelper

  def new
  end 

  def login
    user = User.find_by_email(params[:email])
    if user && user.authenticate(params[:password])
      sign_in user
      #@user = user
      render json: user
    else 
      render json: 'Invalid email/password combination'
    end 
  end 

  def destroy
    sign_out
  end 
end