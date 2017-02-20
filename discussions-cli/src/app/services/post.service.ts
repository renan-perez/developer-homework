import { Injectable } from '@angular/core';
import { Http, Response, RequestMethod }  from '@angular/http';
import { Observable } from 'rxjs';

import { BaseService } from './base.service';
import { ServiceUrlUtil } from './service-url-util';

import { Post } from '../model/post';
import { PostId } from '../model/post-id';
import { SystemResponse } from '../model/system-response';

// Import RxJs required methods
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class PostService extends BaseService<Post, PostId> {

  private saveOrUpdatePostURL = `${ServiceUrlUtil.serverURL}/discussions-core/posts/saveOrUpdatePost`;
  private listPostsByUserEmailURL = `${ServiceUrlUtil.serverURL}/discussions-core/posts/listPostsByUserEmail`;
  private listResponsesURL = `${ServiceUrlUtil.serverURL}/discussions-core/posts/listResponses`;
  private deletePostURL = `${ServiceUrlUtil.serverURL}/discussions-core/posts/deletePost`;

  constructor(protected http: Http) {
    super(http);
  }

  saveOrUpdatePost(post: Post) {
    return super.saveOrUpdate(post, this.saveOrUpdatePostURL);
  }

  deletePost(id: PostId) {
    return super.delete(id, this.deletePostURL);
  }

  listPostsByUserEmail(email: String) {
    const url = `${this.listPostsByUserEmailURL}/${email}/`;
    return super.list(url);
  }

  listResponses(email: String, dateTime: Date) {
    const url = `${this.listResponsesURL}/${email}/${dateTime}/`;
    return super.list(url);
  }

}
