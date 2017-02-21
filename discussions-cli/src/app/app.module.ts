import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { RegisterComponent } from './components/register/register.component';
import { DiscussionsBoardComponent } from './components/discussions-board/discussions-board.component';

import { AppRoutingModule }     from './app-routing.module';

import { UserService } from './services/user.service';
import { PostService } from './services/post.service';
import { GeolocationService } from './services/geolocation.service';
import { WeatherService } from './services/weather.service';


@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    DiscussionsBoardComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule
  ],
  providers: [
    PostService,
    UserService,
    GeolocationService,
    WeatherService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
