import { Component, OnInit } from '@angular/core';

import { Post } from '../../model/post';
import { PostId } from '../../model/post-id';
import { User } from '../../model/user';
import { Location } from '../../model/location';
import { SystemResponse } from '../../model/system-response';

import { PostService } from '../../services/post.service';
import { GeolocationService } from '../../services/geolocation.service';
import { WeatherService } from '../../services/weather.service';
import { UserService } from '../../services/user.service';

import { BaseComponent } from '../base.component';

@Component({
  selector: 'app-discussions-board',
  templateUrl: './discussions-board.component.html',
  styleUrls: ['./discussions-board.component.css']
})
export class DiscussionsBoardComponent extends BaseComponent  implements OnInit {

  private userPosts: Post[];
  private user: User;
  private loggedUser: User;
  private users: User[];
  private systemResponseUsers: SystemResponse<User[]>;
  private postSuperior: Post;
  private systemResponsePost: SystemResponse<Post>;
  private systemResponsePosts: SystemResponse<Post[]>;
  private systemResponseResponses: SystemResponse<Post[]>;
  private commentContent: String;
  private temperature: Number;

  constructor(
    private postService: PostService,
    private geolocationService: GeolocationService,
    private weatherService: WeatherService,
    private userService: UserService
  ) { 
    super();
  }

  ngOnInit() {
    
    this.user = this.getLoggedUser();
    this.loggedUser = this.user;
    this.listPostsUserByEmail(this.user.email);
    this.getGeolocation();
    this.getWeatherInformation();
    this.listUsers();
  }

  getLoggedUser() {
    let user = new User();
    user.name = localStorage.getItem("currentUserName");
    user.email = localStorage.getItem("currentUserEmail");
    return user;
  }

  listPostsUserByEmail(email: String) {
    this.postService.listPostsByUserEmail(email)
          .subscribe(
            (response: SystemResponse<Post[]>) => this.systemResponsePosts = response,
            err => console.log(err),
            () => this.validatePostsResponse(this.systemResponsePosts)
          );
  }

  changeUser(user: User) {
    this.user = user;
    this.listPostsUserByEmail(user.email);
  }

  listUsers() {
    this.userService.listUser()
          .subscribe(
            (response: SystemResponse<User[]>) => this.systemResponseUsers = response,
            err => console.log(err),
            () => this.validateUsersResponse(this.systemResponseUsers)
          );
  }

  getResponses(newPostSuperior: Post, isBack: Boolean, isReply: Boolean) {

    if (!isBack && !isReply) {
      newPostSuperior.superiorPost = this.postSuperior;
    }
    this.postService.listResponses(newPostSuperior.id.user.email, newPostSuperior.id.dateTime)
          .subscribe(
            (response: SystemResponse<Post[]>) => this.systemResponseResponses = response,
            err => console.log(err),
            () => this.validateResponsesResponse(this.systemResponseResponses, newPostSuperior)
          );
  }

  back() {
    if (this.postSuperior.superiorPost == null) {    
      this.listPostsUserByEmail(this.user.email);
    } else {
      this.postSuperior = this.postSuperior.superiorPost;
      this.getResponses(this.postSuperior, true, false);
    }
  }

  reply(content: String, superiorPost: Post) {
    if (content != null && content != '') {
      this.postService.saveOrUpdatePost(this.createPostInstance(content, superiorPost))
            .subscribe(
              (response: SystemResponse<Post>) => this.systemResponsePost = response,
              err => console.log(err),
              () => this.validatePostResponse(this.systemResponsePost, false, true, superiorPost)
            );
    }
  }

  deletePost(id: PostId) {
  this.postService.deletePost(id)
          .subscribe(
            (response: SystemResponse<Post>) => this.systemResponsePost = response,
            err => console.log(err),
            () => this.validatePostResponse(this.systemResponsePost, false, true, null)
          );
  }

  deleteAllowed(id: PostId) {
    return this.loggedUser.email == id.user.email;
  }

  getGeolocation() {
    if (localStorage.getItem("browserHasGeolocation") == 'true') {
      let latitude = localStorage.getItem("latitude");
      let longitude = localStorage.getItem("longitude");
      let geolocation: any;
      this.geolocationService.getLocation(latitude, longitude)
            .subscribe(
              (response: any) => geolocation = response,
              err => console.log(err),
              () => this.validateGeolocalization(geolocation)
            );
    }
  }

  getWeatherInformation() {
    if (localStorage.getItem("browserHasGeolocation") == 'true') {
      let city = localStorage.getItem("city");
      let region = localStorage.getItem("regions");
      let weatherInformation: any;
      this.weatherService.getWeatherInformation(city, region)
            .subscribe(
              (response: any) => weatherInformation = response,
              err => console.log(err),
              () => this.validateWatherInformation(weatherInformation)
            );
    }
  }

  validateWatherInformation(weatherInformation: any) {
    if (weatherInformation != null) {
      this.temperature = Math.trunc(weatherInformation.main.temp);
    }
  }

  validateGeolocalization(geolocation: any) {
    if (geolocation != null && geolocation.results[1].formatted_address != undefined) {
      let formatedAdress = geolocation.results[1].formatted_address;
      localStorage.setItem("adress", formatedAdress);

      let adress: String[] = formatedAdress.split(',', 2);
      localStorage.setItem("city", adress[0].trim());
      localStorage.setItem("region", adress[1].trim());
    }
  }

  validatePostResponse(response: SystemResponse<Post>, isBack: boolean, isReply: Boolean, superiorPost: Post) {
    if(response != null && super.validateResponse(response.status)) {
      if (this.postSuperior != null) {
        this.getResponses(this.postSuperior, isBack, isReply);
      } else {
        this.listPostsUserByEmail(this.user.email);
      }

      if (isReply && superiorPost != null) {
        superiorPost.reply = false;
        superiorPost.replyContent = null;
      } else {
        this.commentContent = null;
      }
    }
  }

  validatePostsResponse(response: SystemResponse<Post[]>) {
    if(response != null && response.content != null) {
      this.userPosts = response.content;
      this.postSuperior = null;
    }
  }

  validateUsersResponse(response: SystemResponse<User[]>) {
    if(response != null && super.validateResponse(response.status)) {
      this.users = response.content;
    }
  }

  validateResponsesResponse(response: SystemResponse<Post[]>, postSuperior: Post) {
    if(response != null && response.content != null) {
      this.postSuperior = postSuperior;
      this.postSuperior.responses = response.content;
      this.userPosts = [postSuperior];
    }
  }

  initReply(post: Post) {
    post.reply = true;
  }

  cancelReply(post: Post) {
    post.reply = false;
  }

  formatDate(date: Date) {
    let parts = date.toString().trim().split(',', 6);
    let formatedDate = '';
    formatedDate = `${parts[0]}/${parts[1]}/${parts[2]} ${parts[3]}:${parts[4]}`;
    return parts.length == 6 ? `${formatedDate}:${parts[5]}` : `${formatedDate}:0`
  }

  private createPostInstance(content: String, superiorPost: Post) {
    let id: PostId = new PostId();
    id.user = this.loggedUser;
   
    let newPost: Post = new Post();
    newPost.id = id;
    newPost.content = content;
    newPost.superiorPost = superiorPost;
    newPost.currentTemperature = this.temperature;
    newPost.location = this.getLocationsInstance();
    return newPost;
  }

  getLocationsInstance() {
    if (localStorage.getItem("browserHasGeolocation") == 'true') {
      let location: Location = new Location();
      location.adress = localStorage.getItem("adress");
      location.latitude = localStorage.getItem("latitude");
      location.longitude = localStorage.getItem("longitude");
      return location;
    } 
    return null;
  }

  truncate(coords: String) {
    if (coords.charAt(0) == '-') {
      return coords.substring(0,6);
    }
    return coords.substring(0,5);
  }

}