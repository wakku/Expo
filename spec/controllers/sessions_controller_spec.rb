require 'spec_helper'

describe SessionsController do

	let(:valid_attributes) { { "name" => "someName", "email" => "some@email.com", 
		"password" => "somecode", "password_confirmation" => "somecode" } }

	it "should return the user info when I sign in" do
		user = User.create! valid_attributes
		get :login, {:email => user.email.to_param, :password => user.password.to_param, 
			:password_confirmation => user.password_confirmation.to_param}
		assigns(:current_user).should eq(user)
	end
end