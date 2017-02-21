import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { RegisterComponent }        from './components/register/register.component';
import { DiscussionsBoardComponent } from './components/discussions-board/discussions-board.component';

const routes: Routes = [
    { path: '',                             redirectTo: '/discussions', pathMatch: 'full' },
    { path: 'discussions',                  component: RegisterComponent },
    { path: 'discussions/discussionsBoard',   component: DiscussionsBoardComponent },
    { path: '**',           redirectTo: 'discussions', pathMatch: 'full' },
];

@NgModule({
    imports: [ RouterModule.forRoot(routes) ],
    exports: [ RouterModule ]
})
export class AppRoutingModule {}